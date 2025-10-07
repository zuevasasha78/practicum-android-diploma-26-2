package ru.practicum.android.diploma.db.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.db.data.VacancyDbConvertor
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

class VacancyDbRepositoryImpl(
    private val vacancyDao: VacancyDao,
    private val vacancyDbConvertor: VacancyDbConvertor
) : VacancyDbRepository {

    override suspend fun addVacancy(vacancy: VacancyDetail): Boolean {
        val vacancyEntity = vacancyDbConvertor.convertToVacancy(vacancy)
        val result = vacancyDao.addVacancy(vacancyEntity)
        return result.toInt() != -1
    }

    override suspend fun deleteVacancy(vacancyId: String): Boolean {
        val result = vacancyDao.deleteVacancy(vacancyId)
        return result != 0
    }

    override fun getVacancies(): Flow<List<VacancyDetail>?> = flow<List<VacancyDetail>?> {
        val entities = vacancyDao.getVacancies()
        val domainVacancies = entities.map { vacancyDbConvertor.convertToVacancy(it) }
        emit(domainVacancies)
    }.catch {
        emit(null)
    }.flowOn(Dispatchers.IO)

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return vacancyDao.getVacancy(vacancyId) != null
    }
}
