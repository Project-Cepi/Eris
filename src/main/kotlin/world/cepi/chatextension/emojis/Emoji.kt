package world.cepi.chatextension.emojis

/** Represents an emoji with a name and a replace value. */
data class Emoji(val name: String, val value: String) {
    companion object {
        val emojis = listOf(
                Emoji("chicken", "\uEFF1"),
                Emoji("fish", "\uEFF2"),
                Emoji("toad", "\uEff3"),
                Emoji("armor", "\uEff4"),
                Emoji("llama", "\uEff5"),
                Emoji("eye", "\uEff6")
        )
    }
}