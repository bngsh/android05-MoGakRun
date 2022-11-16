package com.whyranoid.data

import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

val runner1 = "123123"
val runner2 = "dsfasdf"
val runner3 = "sdfa9123"
val runner4 = "lknk131"
val runner5 = "10293jhd"

fun writeRunner() {
    db.collection("Runners")
        .document("runnersId")
        .update(
            runner3,
            ""
        )
}

fun deleteRunner() {
    val delete = hashMapOf<String, Any>(
        runner2 to FieldValue.delete()
    )
    db.collection("Runners")
        .document("runnersId")
        .update(
            delete
        ).addOnCompleteListener {
            println("삭제 성공")
        }
}

fun getRunnerCount(): Flow<Int> = callbackFlow {
    db.collection("Runners")
        .document("runnersId")
        .addSnapshotListener { snapshot, exception ->
            snapshot?.let {
                val data = it.data?.size ?: -1
                trySend(data)
                println("러닝 ${it.data?.size}")
            } ?: println("러닝 러닝하는 사람 없음")
        }

    awaitClose()
}