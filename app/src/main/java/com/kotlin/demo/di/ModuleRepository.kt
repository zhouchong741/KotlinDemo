package com.kotlin.demo.di

import android.content.Context
import com.kotlin.demo.respository.IDataStoreRepository
import com.kotlin.demo.respository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * @author: zhouchong
 * 创建日期: 2020/10/16-9:49
 * 描述：
 * 修改人：
 * 迭代版本：
 * 迭代说明：
 */
@Module
@InstallIn(ApplicationComponent::class)
class ModuleRepository {

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): IDataStoreRepository {
        return DataStoreRepository(context)
    }
}