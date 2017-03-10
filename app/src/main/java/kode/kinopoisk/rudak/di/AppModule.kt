package kode.kinopoisk.rudak.di


import dagger.Module
import dagger.Provides
import kode.kinopoisk.rudak.KinopoiskApp
import javax.inject.Singleton

@Module
class AppModule(private val app: KinopoiskApp) {


    @Provides
    @Singleton
    internal fun provideApp(): KinopoiskApp {
        return app
    }
}