package world.cepi.chatextension.emojis

/** Represents an emoji with a name and a replace value. */
data class Emoji(val name: String, val value: String) {
    companion object {
        val emojis = arrayOf(
                Emoji("chicken", "\uE000"),
                Emoji("fish", "\uE001"),
                Emoji("toad", "\uE002"),
                Emoji("armor", "\uE003"),
                Emoji("llama", "\uE004"),
                Emoji("eye", "\uE005")
        )
    }
}