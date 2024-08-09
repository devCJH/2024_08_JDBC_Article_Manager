package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Member;
import com.koreaIT.JAM.service.MemberService;
import com.koreaIT.JAM.session.Session;

public class MemberController {

	private Scanner sc;
	private MemberService memberService;
	
	public MemberController(Connection conn, Scanner sc) {
		this.sc = sc;
		this.memberService = new MemberService(conn);
	}
	
	public void doJoin() {
		if (Session.getLoginedMemberId() != -1) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}
		
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수 입력 정보입니다");
				continue;
			}
			
	        boolean isLoginIdDup = memberService.isLoginIdDup(loginId);
	        
	        if (isLoginIdDup) {
	        	System.out.printf("[ %s ]은(는) 이미 사용중인 아이디입니다\n", loginId);
            	continue;
	        }
	        System.out.printf("[ %s ]은(는) 사용가능한 아이디입니다\n", loginId);
			
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 정보입니다");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			String loginPwChk = sc.nextLine().trim();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 다릅니다.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름은 필수 입력 정보입니다");
				continue;
			}
			break;
		}
		
		memberService.joinMember(loginId, loginPw, name);
		
		System.out.printf("[ %s ]님의 가입이 완료되었습니다\n", name);
	}

	public void doLogin() {
		if (Session.getLoginedMemberId() != -1) {
			System.out.println("로그아웃 후 이용해주세요");
			return;
		}
		
		String loginId = null;
		String loginPw = null;
		Member member = null;
		
		while(true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			
			member = memberService.getMemberByLoginId(loginId);
			
			if (member == null) {
				System.out.printf("[ %s ]은(는) 존재하지 않는 아이디입니다\n", loginId);
				continue;
			}
			
			if (member.getLoginPw().equals(loginPw) == false) {
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}
			break;
		}
		
		Session.login(member.getId());
		
		System.out.printf("[ %s ] 회원님 환영합니다~!\n", member.getName());
	}

	public void doLogout() {
		if (Session.getLoginedMemberId() == -1) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		Session.logout();
		System.out.println("정상적으로 로그아웃 되었습니다");
	}
}