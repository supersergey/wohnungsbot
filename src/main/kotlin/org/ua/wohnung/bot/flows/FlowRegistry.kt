package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.Role

class FlowRegistry(private val userService: UserService, vararg flows: Flow) {

    private val flowMap: Map<Role, Flow>

    init {
        flowMap = flows
            .onEach { it.initialize() }
            .associateBy { it.supportedRole }
    }

    fun getFlowByUserId(userId: Int): Flow {
        val suggestedRole = when (val userRole = userService.findUserRoleById(userId)) {
            null -> Role.GUEST
            org.ua.wohnung.bot.persistence.generated.enums.Role.USER -> {
                if (userService.findById(userId).isComplete())
                    Role.USER
                else
                    Role.GUEST
            }
            else -> Role.valueOf(userRole.name)
        }
        return requireNotNull(flowMap[suggestedRole])
    }

    private fun UserDetails?.isComplete(): Boolean =
        listOfNotNull(
            this?.id,
            this?.bundesland,
            this?.phone,
            this?.pets,
            this?.firstLastName,
            this?.numberOfTenants
        ).size == 6
}
