package com.ssafy.ssafit.controller;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ssafit.model.dto.User;
import com.ssafy.ssafit.model.dto.Video;
import com.ssafy.ssafit.model.service.LikeService;
import com.ssafy.ssafit.model.service.ReviewService;
import com.ssafy.ssafit.model.service.UserService;
import com.ssafy.ssafit.model.service.VideoService;
import com.ssafy.ssafit.util.EncryptUtil;
import com.ssafy.ssafit.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api-user")
@Api(tags="User 컨트롤러")
@CrossOrigin("*")
public class UserRestController {

	@Autowired
	UserService us;
	
	@Autowired
	LikeService ls;
	
	@Autowired
	VideoService vs;
	
	@Autowired
	ReviewService rs;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private EncryptUtil encryptutil;
	
	@Autowired
	JavaMailSender emailsender;
	
	ObjectMapper mapper = new ObjectMapper();
	BufferedReader rd;
	StringBuilder sb;

	@GetMapping("/list")
	@ApiOperation(value = "등록된 모든 사용자 정보를 반환한다.", response = User.class)
	public ResponseEntity<List<User>> selectAll() {
		List<User> list = us.selectAll();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	@ApiOperation(value = "특정 사용자 정보를 반환한다.")
	public ResponseEntity<?> selectUserById(@PathVariable String userId, @RequestBody String loginUser){
		String loginChk = us.loginChk(userId, loginUser);
		if(loginChk.equals("OK")) {
			User user = us.selectUserById(userId);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(loginChk,HttpStatus.BAD_REQUEST);
		}
		
	}
	@Transactional
	@PostMapping("/insert")
	@ApiOperation(value = "새로운 사용자 정보를 등록한다.")
	public ResponseEntity<String> insert(@RequestBody User user) {
		System.out.println(user.toString());
		// 아이디 중복 검사
		if (us.selectUserById(user.getUserId()) != null) {
			return new ResponseEntity<String>("IdDuplication",HttpStatus.NOT_ACCEPTABLE);
		}
		// 비밀번호 유효성 검사
		if (!us.passwordChk(user.getPassword())) {
			return new ResponseEntity<String>("BadPassword",HttpStatus.BAD_REQUEST);
		}
		// 비밀번호 암호화
		user.setPassword(encryptutil.getEncrypt(user.getPassword()));
		us.insertUser(user);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}
	
	@PostMapping("/mail/{email}")
	@ApiOperation(value = "회원가입용 인증번호를 전송합니다")
	public ResponseEntity<?> sendMail(@PathVariable String email){
		if(us.selectUserByEmail(email) != null) {
			return new ResponseEntity<String>("EmailDuplication",HttpStatus.NOT_ACCEPTABLE);
		}
		if(!us.emailChk(email)) return new ResponseEntity<String>("BadEmail",HttpStatus.BAD_REQUEST);
		try {
			int randomCode = (int)((Math.random() + 1) * 100000 ); // 랜덤 여섯자리 숫자
			MimeMessage message = emailsender.createMimeMessage();
			// 수신자 메일 주소
			message.addRecipients(Message.RecipientType.TO, email);
			// Subject(제목)
			message.setSubject("test 인증입니다");
			// Text(본문)
			message.setText("사실 코드 테스트용 메일이지롱~ : " + randomCode,"utf-8", "html");
			// 보내는 사람
			message.setFrom(new InternetAddress("juho169813@gmail.com","YANADO"));
			emailsender.send(message); // send message
			return new ResponseEntity<Integer>(randomCode, HttpStatus.OK);
		}catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>("UnsupportedEncodingException", HttpStatus.BAD_GATEWAY);
		} catch (MessagingException e) {
			return new ResponseEntity<String>("MessagingException", HttpStatus.BAD_GATEWAY);
		}
	}

	@Transactional
	@PutMapping("/update")
	@ApiOperation(value = "유저 정보를 수정한다.")
	public ResponseEntity<String> update(@RequestBody User user, HttpSession session) {
		String loginChk = us.loginChk(user.getUserId(), (String)session.getAttribute("loginUser"));
		if(!loginChk.equals("OK")) {
			return new ResponseEntity<String>(loginChk, HttpStatus.BAD_REQUEST);
		}
		if (!us.passwordChk(user.getPassword())) {
			return new ResponseEntity<String>("BadPassword",HttpStatus.BAD_REQUEST);
		}
		user.setPassword(encryptutil.getEncrypt(user.getPassword()));
		us.updateUser(user);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("/delete")
	@ApiOperation(value = "회원탈퇴합니다.")
	public ResponseEntity<String> delete(@RequestBody HashMap<String, Object> map) throws Exception{
		User user = mapper.convertValue(us.selectUserById((String)map.get("userId")),User.class);
		String loginUser = (String)map.get("loginUser");
		String chkPassword = (String)map.get("chkPassword");
		String loginChk = us.loginChk(user.getUserId(), loginUser);
		if(loginChk.equals("OK")) {
			if(!user.getPassword().equals(encryptutil.getEncrypt(chkPassword))) {
				return new ResponseEntity<String>("BadPassword",HttpStatus.BAD_REQUEST);				
			}
			List<Video> list = vs.selectAllVideo();
			for(int i=0;i<list.size();i++) {
				String videoId = list.get(i).getVideoId();
				if(ls.didILike(user.getUserId(), videoId).equals("YesIDid")){
					ls.updataLikeCnt(videoId, -1);
				}
				if(rs.didIReview(videoId, user.getUserId())>0) {
					rs.downCommentCnt(rs.didIReview(videoId, user.getUserId()), videoId);
				}
			}
			us.deleteUser(user.getUserId());
			return new ResponseEntity<String>("OK", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(loginChk,HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/login")
	@ApiOperation(value = "로그인을 진행한다.")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = null;
		try {
			User loginUser = us.selectUserById(user.getUserId());
			if(loginUser != null) {
				if (encryptutil.getEncrypt(user.getPassword()).equals(loginUser.getPassword())){
					result.put("accessToken", jwtUtil.createToken("loginId", user.getUserId()));
					result.put("userId", loginUser.getUserId());
					result.put("message", "OK");
					status = HttpStatus.ACCEPTED;
				} else {
					result.put("message", "BadPassword");
					status = HttpStatus.BAD_REQUEST;
				}
			} else {
				result.put("message", "NoSuchUser");
				status = HttpStatus.BAD_REQUEST;
			}
		} catch(UnsupportedEncodingException e) {
			result.put("message", "Error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	@GetMapping("/findId")
	@ApiOperation(value = "아이디를 찾습니다.")
	public ResponseEntity<?> findId(String email, String name){
		User findUser = us.selectUserByEmail(email);
		if(findUser == null) {
			return new ResponseEntity<String>("NoSuchUser",HttpStatus.BAD_REQUEST);
		}
		if(!findUser.getName().equals(name)) {
			return new ResponseEntity<String>("BadName",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(us.selectUserByEmail(email).getUserId(),HttpStatus.OK);
	}
	
	@GetMapping("/findPassword")
	@ApiOperation(value = "비밀번호바꾸기용 인증번호를 전송합니다")
	public ResponseEntity<?> findPassword(String id){
		User findUser = us.selectUserById(id);
		if(findUser == null) {
			return new ResponseEntity<String>("NoSuchUser",HttpStatus.BAD_REQUEST);
		}
		try {
			int randomCode = (int)((Math.random() + 1) * 100000 ); // 랜덤 여섯자리 숫자
			MimeMessage message = emailsender.createMimeMessage();
			// 수신자 메일 주소
			message.addRecipients(Message.RecipientType.TO, findUser.getEmail());
			// Subject(제목)
			message.setSubject("비밀번호 바꾸기용 인증메일입니다");
			// Text(본문)
			message.setText("이거 입력하세용 : " + randomCode,"utf-8", "html");
			// 보내는 사람
			message.setFrom(new InternetAddress("juho169813@gmail.com","YANADO"));
			emailsender.send(message); // send message
			return new ResponseEntity<Integer>(randomCode, HttpStatus.OK);
		}catch (UnsupportedEncodingException e) {
			return new ResponseEntity<String>("UnsupportedEncodingException", HttpStatus.BAD_GATEWAY);
		} catch (MessagingException e) {
			return new ResponseEntity<String>("MessagingException", HttpStatus.BAD_GATEWAY);
		}
	}
	
	@ApiOperation(value = "특정 유저가 좋아요 누른 모든 영상을 가져옵니다.")
	@GetMapping("/{userId}/like")
	public ResponseEntity<?> getLikedVideoByUserId(@PathVariable String userId){
		if(us.selectUserById(userId) == null) {
			return new ResponseEntity<String>("NoSuchUser", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<String>>(ls.selectLikeByUserId(userId),HttpStatus.OK);
	}
}
