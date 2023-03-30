package developer.book.backend.entities.concretes

import jakarta.persistence.*

@Entity
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String? = null,
    @ManyToMany(mappedBy = "authors")
    val books: List<Book>? = null
) {
    constructor(name: String) : this(name = name, books = emptyList())
}