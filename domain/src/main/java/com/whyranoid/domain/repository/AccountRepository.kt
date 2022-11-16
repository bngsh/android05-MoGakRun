package com.whyranoid.domain.repository

import com.whyranoid.domain.model.User

interface AccountRepository {

    // 로컬에서 유저 정보 가져오기
    suspend fun getUser(): Result<User>

    // 파이어베이스에서 uid, 닉네임, 프로필사진 가져오기
    suspend fun loginUser(): Boolean

    // 데이터스토어에서 uid 가져오기
    suspend fun getUid(): Result<String>

    // 데이터스토어에서 닉네임 가져오기
    suspend fun getNickname(): Result<String>

    // 닉네임 수정, 서버에 먼저 보내고 성공하면 로컬에 반영
    // 실패하면 실패 사용자에게 알리기
    suspend fun updateNickname(newNickname: String): Boolean

    // 데이터스토어에서 프로필 이미지 가져오기
    suspend fun getProfileUrl(): Result<String>

    // 프로필 사진 서버에 업데이트
    suspend fun updateProfileUrl(newProfileUrl: String): Boolean
}
