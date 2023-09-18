package io.hyemdooly.di

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ModuleTest {
    private val fakeParentModule = FakeParentModule()
    private val fakeChildModule = FakeChildModule(fakeParentModule)

    @Test
    fun `모듈에서 처음 사용하는 싱글톤 인스턴스는 생성 후 모듈이 가지고 있는 컬렉션에 저장한다`() {
        // given

        // when
        val firstInstance = fakeParentModule.getInstance(FakeDao::class)
        val secondInstance = fakeParentModule.getInstance(FakeDao::class)

        // then
        assertEquals(firstInstance, secondInstance)
    }

    @Test
    fun `모듈에서 해당하는 provider가 없으면 부모 모듈로부터 찾아 사용한다`() {
        // given

        // when
        val instance = fakeChildModule.getInstance(FakeDao::class)

        // then
        assertNotNull(instance)
    }

    @Test
    fun `부모 모듈에도 해당하는 provider가 없으면 직접 생성한다`() {
        // given

        // when
        val instance = fakeChildModule.getInstance(FakePerson::class)

        // then
        assertNotNull(instance)
    }

    @Test
    fun `모듈 자기 자신과 부모 모듈을 모두 사용하여 객체를 생성한다`() {
        // given

        // when
        val instance = fakeChildModule.getInstance(FakeRepository::class)
        val dao = fakeParentModule.getInstance(FakeDao::class)

        // then
        assertAll(
            { assertNotNull(instance) },
            { assertEquals((instance as FakeRepositoryImpl).dao, dao) },
        )
    }
}