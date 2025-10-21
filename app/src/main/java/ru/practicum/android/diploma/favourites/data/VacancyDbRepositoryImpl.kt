package ru.practicum.android.diploma.favourites.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.favourites.data.converter.VacancyDbConvertor
import ru.practicum.android.diploma.favourites.data.db.dao.VacancyDao
import ru.practicum.android.diploma.favourites.domain.db.VacancyDbRepository
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

class VacancyDbRepositoryImpl(
    private val vacancyDao: VacancyDao
) : VacancyDbRepository {

    override suspend fun addVacancy(vacancy: VacancyDetail): Boolean {
        val vacancyEntity = VacancyDbConvertor.convertToVacancy(vacancy)
        val result = vacancyDao.addVacancy(vacancyEntity)
        return result.toInt() != -1
    }

    override suspend fun deleteVacancy(vacancyId: String): Boolean {
        val result = vacancyDao.deleteVacancy(vacancyId)
        return result != 0
    }

    override fun getVacancies(): Flow<List<Vacancy>?> = flow<List<Vacancy>?> {
        val entities = vacancyDao.getVacancies()
        val domainVacancies = entities.map { VacancyDbConvertor.convertToVacancy(it) }
        emit(domainVacancies)
    }.catch {
        emit(null)
    }.flowOn(Dispatchers.IO)

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return vacancyDao.getVacancy(vacancyId) != null
    }

    override suspend fun getVacancyById(vacancyId: String): VacancyDetail? {
        val result = vacancyDao.getVacancy(vacancyId)
        return result?.let {
            VacancyDbConvertor.convertToVacancyDetail(result)
        }
    }
}
