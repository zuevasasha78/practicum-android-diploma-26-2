package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultFilterIndustries
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.search.presentation.models.Placeholder

//class IndustriesInteractorImpl(
//    private val vacancyNetworkRepository: VacancyNetworkRepository,
//    private val sharedPrefInteractor: SharedPrefInteractor
//) : IndustriesInteractor {
//
//    override suspend fun getIndustries(): IndustriesChooserScreenState {
//        return when (val res = vacancyNetworkRepository.getIndustries().convertToApiResultFilterIndustries()) {
//            is ApiResult.NoInternetConnection -> IndustriesChooserScreenState.Error(Placeholder.NoInternet)
//            is ApiResult.Error -> IndustriesChooserScreenState.Error(Placeholder.ServerError)
//            is ApiResult.Success -> {
//                if (res.data.isEmpty()) {
//                    IndustriesChooserScreenState.Empty
//                } else {
//                    IndustriesChooserScreenState.Success(
//                        industries = res.data,
//                        isChosen = getSelectedIndustry().id != -1
//                    )
//                }
//            }
//        }
//    }
//
//    override fun getSelectedIndustry(): FilterIndustry {
//        return sharedPrefInteractor.getChosenIndustry()
//    }
//
//    override fun saveSelectedIndustry(industry: FilterIndustry) {
//        sharedPrefInteractor.setIndustry(industry)
//    }
//
//    override fun clearSelectedIndustry() {
//        sharedPrefInteractor.resetIndustry()
//    }
//}

// ТЕСТОВАЯ ВЕРСИЯ IndustriesInteractorImpl для отладки
class IndustriesInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository,
    private val sharedPrefInteractor: SharedPrefInteractor
) : IndustriesInteractor {

    // Режимы тестирования:
    // 0 - реальные данные
    // 1 - тестовые данные (успех)
    // 2 - ошибка сети
    // 3 - ошибка сервера
    // 4 - пустой список
    private val testMode = 1

    override suspend fun getIndustries(): IndustriesChooserScreenState {
        return when (testMode) {
            0 -> getRealIndustries()
            1 -> getTestIndustriesSuccess()
            2 -> getTestIndustriesNoInternet()
            3 -> getTestIndustriesServerError()
            4 -> getTestIndustriesEmpty()
            else -> getRealIndustries()
        }
    }

    private suspend fun getRealIndustries(): IndustriesChooserScreenState {
        return when (val res = vacancyNetworkRepository.getIndustries().convertToApiResultFilterIndustries()) {
            is ApiResult.NoInternetConnection -> IndustriesChooserScreenState.Error(Placeholder.NoInternet)
            is ApiResult.Error -> IndustriesChooserScreenState.Error(Placeholder.ServerError)
            is ApiResult.Success -> {
                if (res.data.isEmpty()) {
                    IndustriesChooserScreenState.Empty
                } else {
                    IndustriesChooserScreenState.Success(
                        industries = res.data,
                        isChosen = getSelectedIndustry().id != -1
                    )
                }
            }
        }
    }

    private fun getTestIndustriesSuccess(): IndustriesChooserScreenState {
        Thread.sleep(800)
        val testIndustries = createTestIndustries()
        return IndustriesChooserScreenState.Success(
            industries = testIndustries,
            isChosen = getSelectedIndustry().id != -1
        )
    }

    private fun getTestIndustriesNoInternet(): IndustriesChooserScreenState {
        Thread.sleep(500)
        return IndustriesChooserScreenState.Error(Placeholder.NoInternet)
    }

    private fun getTestIndustriesServerError(): IndustriesChooserScreenState {
        Thread.sleep(500)
        return IndustriesChooserScreenState.Error(Placeholder.ServerError)
    }

    private fun getTestIndustriesEmpty(): IndustriesChooserScreenState {
        Thread.sleep(300)
        return IndustriesChooserScreenState.Empty
    }

    private fun createTestIndustries(): List<FilterIndustry> {
        return listOf(
            FilterIndustry(id = 1, name = "IT, интернет, телеком"),
            FilterIndustry(id = 2, name = "Банки, инвестиции, лизинг"),
            FilterIndustry(id = 3, name = "Гостиницы, рестораны, общепит"),
            FilterIndustry(id = 4, name = "Государственная служба"),
            FilterIndustry(id = 5, name = "Добыча сырья"),
            FilterIndustry(id = 6, name = "Домашний персонал"),
            FilterIndustry(id = 7, name = "ЖКХ, эксплуатация"),
            FilterIndustry(id = 8, name = "Искусство, развлечения, масс-медиа"),
            FilterIndustry(id = 9, name = "Маркетинг, реклама, PR"),
            FilterIndustry(id = 10, name = "Медицина, фармацевтика"),
            FilterIndustry(id = 11, name = "Недвижимость"),
            FilterIndustry(id = 12, name = "Образование, наука"),
            FilterIndustry(id = 13, name = "Общественная деятельность"),
            FilterIndustry(id = 14, name = "Перевозки, логистика, ВЭД"),
            FilterIndustry(id = 15, name = "Продажи"),
            FilterIndustry(id = 16, name = "Производство, сельское хозяйство"),
            FilterIndustry(id = 17, name = "Страхование"),
            FilterIndustry(id = 18, name = "Строительство, недвижимость"),
            FilterIndustry(id = 19, name = "Туризм, гостиницы, рестораны"),
            FilterIndustry(id = 20, name = "Управление персоналом, тренинги")
        ).sortedBy { it.name }
    }

    override fun getSelectedIndustry(): FilterIndustry {
        return sharedPrefInteractor.getChosenIndustry()
    }

    override fun saveSelectedIndustry(industry: FilterIndustry) {
        sharedPrefInteractor.setIndustry(industry)
    }

    override fun clearSelectedIndustry() {
        sharedPrefInteractor.resetIndustry()
    }
}
