import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day00KtTest {

    @Test
    fun canAdd() {
        assertThat(sum(1, 2)).isEqualTo(3)
    }
}