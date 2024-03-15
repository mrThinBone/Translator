package core.domain.language

import java.util.Locale

fun Language.toLocale(): Locale? {
    return when(this) {
        Language.ENGLISH -> Locale.ENGLISH
        Language.CHINESE -> Locale.CHINESE
        Language.FRENCH -> Locale.FRENCH
        Language.GERMAN -> Locale.GERMAN
        Language.ITALIAN -> Locale.ITALIAN
        Language.JAPANESE -> Locale.JAPANESE
        Language.KOREAN -> Locale.KOREAN
        else -> null
    }
}