package com.example.employeedirection.feature.employee_list.domain.model


import com.google.gson.annotations.SerializedName

data class Employee(
    val team: String,
    val uuid: String,
    @SerializedName("email_address")
    val emailAddress: String,
    @SerializedName("employee_type")
    val employeeType: EmployeeType,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("photo_url_large")
    val photoUrlLarge: String?,
    @SerializedName("photo_url_small")
    val photoUrlSmall: String?,
    val biography: String?
) {
    fun isInValidData(): Boolean {
        // here only check empty or null value, and assume the format of the content of all of
        // them are valid, such as email, name, etc
        return setOf(
            team,
            uuid,
            emailAddress,
            fullName,
            employeeType.name
        // here we check null because Gson can put null value in non-nullable field
        ).any { it.isNullOrEmpty() }
    }

    //this for DiffUtil's reference when updating RecyclerView
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (team != other.team) return false
        if (emailAddress != other.emailAddress) return false
        if (fullName != other.fullName) return false
        if (photoUrlSmall != other.photoUrlSmall) return false

        return true
    }

    override fun hashCode(): Int {
        var result = team.hashCode()
        result = 31 * result + emailAddress.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + (photoUrlSmall?.hashCode() ?: 0)
        return result
    }


}



enum class EmployeeType {
    @SerializedName("FULL_TIME")
    FULL_TIME,

    @SerializedName("PART_TIME")
    PART_TIME,

    @SerializedName("CONTRACTOR")
    CONTRACTOR
}

