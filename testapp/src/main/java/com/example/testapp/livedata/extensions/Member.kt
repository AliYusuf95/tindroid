package com.example.testapp.livedata.extensions

import com.example.testapp.client.models.Member
import com.example.testapp.client.models.User


/** Updates collection of members with more recent data of [users]. */
internal fun Collection<Member>.updateUsers(userMap: Map<String, User>): Collection<Member> = map { member ->
    if (userMap.containsKey(member.getUserId())) {
        member.copy(user = userMap[member.getUserId()] ?: member.user)
    } else {
        member
    }
}
