import org.springframework.stereotype.Component
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import java.time.LocalDate

data class Institution(
    val name: String,
    val accreditationLevel: String,
    val address: String,
    val establishmentDate: String,
    val numberOfFaculties: Int,
    val website: String,
    val hasMilitaryDepartment: Boolean,
    val disciplines: List<String>
) : Comparable<Institution> {

    override fun compareTo(other: Institution): Int {
        val nameComparison = name.compareTo(other.name)
        return if (nameComparison != 0) {
            nameComparison
        } else {
            numberOfFaculties.compareTo(other.numberOfFaculties)
        }
    }
}

data class Discipline(
    val name: String,
    val institution: String,
    val specialtyCode: String,
    val semester: Int,
    val numberOfHours: Int,
    val approvalDate: String,
    val hasExam: Boolean
) : Comparable<Discipline> {

    override fun compareTo(other: Discipline): Int {
        val nameComparison = name.compareTo(other.name)
        return if (nameComparison != 0) {
            nameComparison
        } else {
            numberOfHours.compareTo(other.numberOfHours)
        }
    }
}
interface Container<T> {
    /**
    Додає новий елемент до контейнера.
    @param item елемент для додавання.
     */
    fun add(item: T)
    /**
    Видаляє елемент з контейнера за індексом.
    @param index індекс елемента, який треба видалити.
     */
    fun remove(index: Int)
    /**
    Оновлює елемент у контейнері за індексом.
    @param index індекс елемента, який треба оновити.
    @param item новий елемент для оновлення.
     */
    fun update(index: Int, item: T)
    /**
    Повертає елемент з контейнера за індексом.
    @param index індекс елемента, який треба повернути.
    @return елемент з контейнера за індексом.
     */
    fun get(index: Int): T
    /**
    Повертає всі елементи з контейнера.
    @return всі елементи з контейнера.
     */
    fun getAll(): List<T>
}
@Component
class InstitutionContainer : Container<Institution> {

    private val institutions = mutableListOf<Institution>()

    override fun add(item: Institution) {
        institutions.add(item)
    }

    override fun remove(index: Int) {
        institutions.removeAt(index)
    }

    override fun update(index: Int, item: Institution) {
        institutions[index] = item
    }

    override fun get(index: Int): Institution {
        return institutions[index]
    }

    override fun getAll(): List<Institution> {
        return institutions
    }
}

fun main() {
    val context = AnnotationConfigApplicationContext()
    context.register(InstitutionContainer::class.java)
    context.refresh()

    val container = context.getBean(Container::class.java) as InstitutionContainer

    val institution1 = Institution("Institution 1", "Level 1", "Address 1", "2000-01-01",
        5, "www.institution1.com", true, listOf("Discipline A", "Discipline B"))

    val institution2 = Institution("Institution 2", "Level 2", "Address 2", "2005-01-01",
        3, "www.institution2.com", false, listOf("Discipline C", "Discipline D"))

    val institution3 = Institution("Institution 3", "Level 1", "Address 3", "2010-01-01",
        6, "www.institution3.com", true, listOf("Discipline E", "Discipline F"))

    val institution4 = Institution("Institution 4", "Level 3", "Address 4", "2015-01-01",
        2, "www.institution4.com", false, listOf("Discipline G", "Discipline H"))

    val institution5 = Institution("Institution 5", "Level 2", "Address 5", "2020-01-01",
        4, "www.institution5.com", true, listOf("Discipline I", "Discipline J"))

    container.add(institution1)
    container.add(institution2)
    container.add(institution3)
    container.add(institution4)
    container.add(institution5)

    val get = container.get(2)
    println("Institution 2: $get")

    container.remove(3)
    println("Removed institution 3")

    container.getAll().forEach { println(it) }
}