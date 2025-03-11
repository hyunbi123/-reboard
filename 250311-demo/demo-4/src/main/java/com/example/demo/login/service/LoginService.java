package com.example.demo.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.login.repository.LoginJPA;
import com.example.demo.member.model.Member;

@Service
public class LoginService implements UserDetailsService{

	@Autowired
	LoginJPA loginjpa;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		System.out.println("로그인처리서비스");
		//아이디와 비밀번호를 전송하면 아이디만 전송받고
		System.out.println(username);
		//아이디를 이용하여 데이터베이스의 객체를 가지고 온 후 
		Optional<Member> member=loginjpa.findByUsername(username);
		//System.out.println(member.get());
		//객체를 전송받은지 여부 확인 후 오류처리
		if(member.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		//권한에 대해 입력한 후 해당 권한의 의해 리소스를 접근하게 된다.
		//아래의 코드는 권한이 부여되지 않은 리스트이며, 추가적으로 권한을 확인 한 후 처리
		List<GrantedAuthority> authorities
		=new ArrayList<GrantedAuthority>();
		//서비스에서 db의 비밀번호와 폼에서 입력한 비밀번호를 비교하여
		//일치할 경우 UserDatail객체를 반환하고 그렇지 않을 경우 null을 반환
			
		return new User(
				member.get().getUsername(),
				//new BCryptPasswordEncoder().encode(member.get().getPassword()),
				member.get().getPassword(),
				authorities
			);
	}

	/*
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	//아래의 함수가 로그인을 처리해준다.
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		System.out.println("login procedure!!!!!!");
		System.out.println(username);
		// 사용자의 id를 가지고와서 db에서 해당사용자의 정보를 수집하고
		//수집된 정보와 전달받은 패스워드를 비교하여 인증여부 확인
		
		//1)db로부터 id정보를 데이터를 전달받아한다.(db설정)
		try(SqlSession sqlSession=sqlSessionFactory.openSession()){
			MemberMapper memberDao=
			sqlSession.getMapper(MemberMapper.class);
			
			Member member=
			memberDao.selectMemberByUsername(username);
			//uesername을 이용하여 데이터베이스 정보 전달받은 후 계정확인
			System.out.println(member);
			//나의 계정과 username, password
			//db계정정보 member.getUsername, member.getPassword
			//아래의 함수는 폼으로부터 전송한password와 db의 getPassword를 비교
			List<GrantedAuthority> authorities
			=new ArrayList<GrantedAuthority>();
			
			if(member.getRole().equals("ROLE_ADMIN")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}else if(member.getRole().equals("ROLE_USER")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			}else {
				authorities.add(new SimpleGrantedAuthority("ROLE_GUEST"));
			}
			//username정보, 패스워드정보, 권한정보 확인
			/*
			 * 아래의 코드는 암호화가 되어 있지 않아 처리 불가(시큐리티 기본 암호화처리)
			User user=new User(member.getUsername()
					,member.getPassword()
					,authorities);
			*/
			/*
			User user=new User(member.getUsername()
					,new BCryptPasswordEncoder().encode(member.getPassword())
					,authorities);
			*/
			//db에 password가 암호화 되어 있으므로 별도로 엔코드 할 필요가 없음
	/*
			User user=new User(member.getUsername()
					,member.getPassword()
					,authorities);
			//패스워드가 일치하면 객체가 생성, 그렇지 않으면 null값 처리
			//폼에서 전송된 패스워드는 해당코드에서는 나타나지 않으며, User객체가 알아서 비교를 해준다.
			System.out.println(user);
			System.out.println(authorities);
			return user;
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
*/
}
