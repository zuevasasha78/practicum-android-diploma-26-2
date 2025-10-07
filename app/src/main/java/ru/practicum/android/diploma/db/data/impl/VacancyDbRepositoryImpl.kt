package ru.practicum.android.diploma.db.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.db.data.VacancyDbConvertor
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.domain.models.Vacancy

class VacancyDbRepositoryImpl(
    private val vacancyDao: VacancyDao,
    private val vacancyDbConvertor: VacancyDbConvertor
) : VacancyDbRepository {

    override suspend fun addVacancy(vacancy: VacancyEntity) {
        // todo сконвертировать Модель в VacancyDbConvertor через VacancyDbConvertor
        vacancyDao.addVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancyId: String) {
        vacancyDao.deleteVacancy(vacancyId)
    }

    override fun getVacancies(): Flow<List<Vacancy>?> = flow<List<Vacancy>?> {
        val entities = vacancyDao.getVacancies()
        val domainVacancies = entities.map { vacancyDbConvertor.convertToVacancy(it) }
        emit(domainVacancies)
    }.catch {
        emit(null)
    }.flowOn(Dispatchers.IO)
}
