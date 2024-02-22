package com.jj.dheaven.service;

import com.jj.dheaven.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

    private final MemberRepository memberRepository;






}
