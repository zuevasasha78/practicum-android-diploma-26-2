package ru.practicum.android.diploma.db.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.db.domain.VacancyRepository

class VacancyRepositoryImpl(private val vacancyDao: VacancyDao) : VacancyRepository {

    override suspend fun addVacancy(vacancy: VacancyEntity) {
        // todo сконвертировать Модель в VacancyDbConvertor через VacancyDbConvertor
        vacancyDao.addVacancy(vacancy)
    }

    override suspend fun deleteVacancy(vacancyId: String) {
        vacancyDao.deleteVacancy(vacancyId)
    }

    override suspend fun getVacancies(): Flow<List<VacancyEntity>?> = flow {
        val vacanciesEntityList = vacancyDao.getVacancies()
        emit(vacanciesEntityList)
    }
}
