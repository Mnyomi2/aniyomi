package eu.kanade.presentation.more.settings.screen.browse

import dev.icerock.moko.resources.StringResource
import kotlinx.collections.immutable.ImmutableSet
import mihon.domain.extensionrepo.model.ExtensionRepo

sealed interface RepoScreenState {
    data object Loading : RepoScreenState

    data class Success(
        val repos: ImmutableSet<ExtensionRepo>,
        val dialog: RepoDialog? = null,
    ) : RepoScreenState
}

sealed interface RepoDialog {
    data object Create : RepoDialog

    data class Delete(val repo: String) : RepoDialog

    data class Conflict(
        val oldRepo: ExtensionRepo,
        val newRepo: ExtensionRepo,
    ) : RepoDialog

    data class Confirm(val url: String) : RepoDialog
}

sealed interface RepoEvent {
    data object InvalidUrl : RepoEvent

    data object RepoAlreadyExists : RepoEvent

    data class LocalizedMessage(val stringRes: StringResource) : RepoEvent
}
