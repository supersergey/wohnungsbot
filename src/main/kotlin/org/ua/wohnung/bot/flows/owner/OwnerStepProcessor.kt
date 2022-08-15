// package org.ua.wohnung.bot.flows.owner
//
// import mu.KotlinLogging
// import org.ua.wohnung.bot.account.AccountService
// import org.ua.wohnung.bot.exception.ServiceException
// import org.ua.wohnung.bot.exception.UserInputValidationException
// import org.ua.wohnung.bot.flows.dto.ChatMetadata
// import org.ua.wohnung.bot.flows.processors.StepProcessor
// import org.ua.wohnung.bot.flows.step.FlowStep
// import org.ua.wohnung.bot.persistence.generated.enums.Role
// import org.ua.wohnung.bot.user.UserService
//
// sealed class OwnerStepProcessor(private val userService: UserService) : StepProcessor {
//
//    protected val logger = KotlinLogging.logger { }
//
//    protected fun resolveUserId(input: String): Long {
//        return runCatching { input.toLong() }.getOrElse {
//            throw UserInputValidationException.InvalidUserId(input)
//        }
//    }
//
//    protected fun validateOwnerPermission(userId: Long) {
//        val currentUserRole = userService.findUserRoleById(userId)
//        if (currentUserRole != Role.OWNER) {
//            throw ServiceException.AccessViolation(userId, currentUserRole, Role.OWNER)
//        }
//    }
//
//    class AddAdmin(
//        private val accountService: AccountService,
//        userService: UserService
//    ) : OwnerStepProcessor(userService) {
//
//        override val stepId = FlowStep.OWNER_ADD_ADMIN
//        override fun invoke(chatMetadata: ChatMetadata, input: String) {
//            validateOwnerPermission(chatMetadata.userId)
//            val userId = resolveUserId(input)
//            accountService.updateUserRole(userId, Role.ADMIN)
//            logger.info { "User $userId has been granted ADMIN privileges" }
//        }
//    }
//
//    class RemoveAdmin(
//        private val accountService: AccountService,
//        val userService: UserService
//    ) : OwnerStepProcessor(userService) {
//
//        override val stepId = FlowStep.OWNER_REMOVE_ADMIN
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String) {
//            validateOwnerPermission(chatMetadata.userId)
//            val userId = resolveUserId(input)
//            accountService.updateUserRole(userId, Role.USER)
//            logger.info { "User $userId has been granted USER privileges" }
//        }
//    }
// }
