package com.example.swings.service;

import com.example.swings.dto.UserDTO;
import com.example.swings.entity.User;
import com.example.swings.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service //스프링이 관리해주는 객체 == 스프링 빈
@RequiredArgsConstructor //controller와 같이. final 멤버변수 생성자 만드는 역할
public class UserService {

    private final UserRepository userRepository; // 먼저 jpa, mysql dependency 추가

    public void save(UserDTO userDTO) {
        // repsitory의 save 메서드 호출
        User user = User.toUser(userDTO);
        userRepository.save(user);
        //Repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)

    }

//    public MemberDTO login(UserDTO userDTO){ //entity객체는 service에서만
//        Optional<MemberEntity> byUserEmail = memberRepository.findByUserEmail(memberDTO.getUserEmail());
//        if(byUserEmail.isPresent()){
//            // 조회 결과가 있다
//            MemberEntity memberEntity = byUserEmail.get(); // Optional에서 꺼냄
//            if(memberEntity.getUserPwd().equals(memberDTO.getUserPwd())) {
//                //비밀번호 일치
//                //entity -> dto 변환 후 리턴
//                return MemberDTO.fromMemberEntity(memberEntity);
//            } else {
//                //비밀번호 불일치
//                return null;
//            }
//        } else {
//            // 조회 결과가 없다
//            return null;
//        }
//    }

    public User authenticate(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElse(null);
        if (user != null) {
            // 이메일에 해당하는 사용자가 존재하는 경우
            return user.getPassword().equals(userDTO.getPassword()) ? user : null;
        } else {
            // 이메일에 해당하는 사용자가 존재하지 않는 경우
            return null;
        }
    }
}