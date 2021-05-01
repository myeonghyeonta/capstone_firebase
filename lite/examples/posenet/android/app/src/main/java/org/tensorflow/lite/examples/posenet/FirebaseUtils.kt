//명현 firebase 실험

package org.tensorflow.lite.examples.posenet

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


object FirebaseUtils {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
}