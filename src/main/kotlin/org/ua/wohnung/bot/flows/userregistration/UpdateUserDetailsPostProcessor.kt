package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.exception.UserInputValidationException
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.BundesLand

sealed class UpdateUserDetailsPostProcessor(private val userService: UserService) : PostProcessor {
    abstract fun doInvoke(userDetails: UserDetails, input: String)

    override fun invoke(chatMetadata: ChatMetadata, input: String) {
        val userDetails = userService.findById(chatMetadata.userId)
            ?: UserDetails().apply {
                this.id = chatMetadata.userId
            }
        doInvoke(userDetails, input)
        userService.updateUserDetails(userDetails)
    }

    class Bundesland(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.BUNDESLAND_SELECTION

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.bundesland = validateAndGet(input).germanName
        }

        private fun validateAndGet(input: String): BundesLand {
            return BundesLand.values().firstOrNull {
                it.germanName.lowercase() == input.trim().lowercase()
            } ?: throw UserInputValidationException.InvalidBundesLand(input)
        }
    }

    class FirstAndLastNamePostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.FIRSTNAME_AND_LASTNAME

        override fun doInvoke(userDetails: UserDetails, input: String) {
            if (input.length in (3..50)) {
                userDetails.firstLastName = input
            } else
                throw UserInputValidationException.InvalidUserName(input)
        }
    }

    class PhoneNumberPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.PHONE_NUMBER

        override fun doInvoke(userDetails: UserDetails, input: String) {
            val clean = input.replace("\\s*\\D".toRegex(), "")
            if (clean.length in (5..15)) {
                userDetails.phone = clean
            } else
                throw UserInputValidationException.InvalidPhoneNumber(input)
        }
    }

    class PetsPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.PETS

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.pets = when (input) {
                "??????" -> true
                else -> false
            }
        }
    }

    class FamilyCountPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.FAMILY_COUNT

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.numberOfTenants = Integer.parseInt(input).toShort()
        }
    }

    class FamilyMembersPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.FAMILY_MEMBERS

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.familyMembers = input
        }
    }

    class ReadyToMovePostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.READY_TO_MOVE

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.readyToMove = when (input) {
                "??????" -> true
                else -> false
            }
        }
    }

    class DistrictSelectionPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.DISTRICT_SELECTION

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.district = input
        }
    }

    class ForeignLanguagesPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.FOREIGN_LANGUAGES

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.foreignLanguages = input
        }
    }

    class AllergiesPostProcessorUpdate(userService: UserService) : UpdateUserDetailsPostProcessor(userService) {
        override val stepId = FlowStep.ALLERGIES

        override fun doInvoke(userDetails: UserDetails, input: String) {
            userDetails.allergies = input
        }
    }
}
