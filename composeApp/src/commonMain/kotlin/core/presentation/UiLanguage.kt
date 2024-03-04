@file:OptIn(ExperimentalResourceApi::class)

package core.presentation

import core.domain.language.Language
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import translator.composeapp.generated.resources.Res
import translator.composeapp.generated.resources.arabic
import translator.composeapp.generated.resources.azerbaijani
import translator.composeapp.generated.resources.chinese
import translator.composeapp.generated.resources.czech
import translator.composeapp.generated.resources.danish
import translator.composeapp.generated.resources.dutch
import translator.composeapp.generated.resources.english
import translator.composeapp.generated.resources.finnish
import translator.composeapp.generated.resources.french
import translator.composeapp.generated.resources.german
import translator.composeapp.generated.resources.greek
import translator.composeapp.generated.resources.hebrew
import translator.composeapp.generated.resources.hindi
import translator.composeapp.generated.resources.hungarian
import translator.composeapp.generated.resources.indonesian
import translator.composeapp.generated.resources.irish
import translator.composeapp.generated.resources.italian
import translator.composeapp.generated.resources.japanese
import translator.composeapp.generated.resources.korean
import translator.composeapp.generated.resources.persian
import translator.composeapp.generated.resources.polish
import translator.composeapp.generated.resources.portuguese
import translator.composeapp.generated.resources.russian
import translator.composeapp.generated.resources.slovak
import translator.composeapp.generated.resources.spanish
import translator.composeapp.generated.resources.swedish
import translator.composeapp.generated.resources.turkish
import translator.composeapp.generated.resources.ukrainian

class UiLanguage(
    val drawableRes: DrawableResource,
    val language: Language
) {

    /*fun toLocale(): Locale? {
        return when(language) {
            Language.ENGLISH -> Locale.ENGLISH
            Language.CHINESE -> Locale.CHINESE
            Language.FRENCH -> Locale.FRENCH
            Language.GERMAN -> Locale.GERMAN
            Language.ITALIAN -> Locale.ITALIAN
            Language.JAPANESE -> Locale.JAPANESE
            Language.KOREAN -> Locale.KOREAN
            else -> null
        }
    }*/

    companion object {
        fun byCode(langCode: String): UiLanguage {
            return allLanguages.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }
        val allLanguages: List<UiLanguage>
            get() = Language.entries.map { language ->
                UiLanguage(
                    language = language,
                    drawableRes = when(language) {
                        Language.ENGLISH -> Res.drawable.english
                        Language.ARABIC -> Res.drawable.arabic
                        Language.AZERBAIJANI -> Res.drawable.azerbaijani
                        Language.CHINESE -> Res.drawable.chinese
                        Language.CZECH -> Res.drawable.czech
                        Language.DANISH -> Res.drawable.danish
                        Language.DUTCH -> Res.drawable.dutch
                        Language.FINNISH -> Res.drawable.finnish
                        Language.FRENCH -> Res.drawable.french
                        Language.GERMAN -> Res.drawable.german
                        Language.GREEK -> Res.drawable.greek
                        Language.HEBREW -> Res.drawable.hebrew
                        Language.HINDI -> Res.drawable.hindi
                        Language.HUNGARIAN -> Res.drawable.hungarian
                        Language.INDONESIAN -> Res.drawable.indonesian
                        Language.IRISH -> Res.drawable.irish
                        Language.ITALIAN -> Res.drawable.italian
                        Language.JAPANESE -> Res.drawable.japanese
                        Language.KOREAN -> Res.drawable.korean
                        Language.PERSIAN -> Res.drawable.persian
                        Language.POLISH -> Res.drawable.polish
                        Language.PORTUGUESE -> Res.drawable.portuguese
                        Language.RUSSIAN -> Res.drawable.russian
                        Language.SLOVAK -> Res.drawable.slovak
                        Language.SPANISH -> Res.drawable.spanish
                        Language.SWEDISH -> Res.drawable.swedish
                        Language.TURKISH -> Res.drawable.turkish
                        Language.UKRAINIAN -> Res.drawable.ukrainian
                    }
                )
            }.sortedBy { it.language.langName }
    }
}