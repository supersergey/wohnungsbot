package org.ua.wohnung.bot

class UserRegistrationFlowSteps {
    companion object {
        val SUCCESS_STEP = Step.Termination(
            caption = "Дякуємо, ви підписані на розсилку помешкань від групи \"Українці в Берліні\"",
        )
        val FAILED_STEP = Step.Termination(
            caption = "Нажаль, ми не можемо вам допомогти."
        )
        val PETS_STEP = Step.General(
            caption = "У вас є домашні тварини",
            reply = Reply.Inline(
                "Так" to SUCCESS_STEP,
                "Ні" to SUCCESS_STEP
            ),
        )
        val NUMBER_OF_TENANTS_STEP = Step.General(
            caption = "Помешкання на скільки людей ви шукаєте?",
            reply = Reply.Inline(
                *(1..8).map { "$it" }.allTo(PETS_STEP)
            )
        )
        val LAND_SELECTION_STEP = Step.General(
            caption = "В якій федеральній землі Німеччини ви зареєстровані?",
            reply = Reply.Inline(
                *listOf("Берлін", "Баварія", "Саксонія", "Не зареєстрований").allTo(NUMBER_OF_TENANTS_STEP)
            ),
        )
        val PERSONAL_DATA_STEP = Step.General(
            caption = "Ви погоджуєтесь на обробку персональних даних?",
            reply = Reply.Inline(
                "Так" to LAND_SELECTION_STEP,
                "Ні" to FAILED_STEP
            ),
        )
        val START_STEP = Step.General(
            caption = "Доброго вечора, ми з України \uD83C\uDDFA\uD83C\uDDE6",
            reply = Reply.Inline(
                "Зареєструватися" to PERSONAL_DATA_STEP,
                "Видаліть мої дані з системи" to FAILED_STEP
            )
        )

        private fun List<String>.allTo(next: Step) = map { it to next }.toTypedArray()
    }
}
