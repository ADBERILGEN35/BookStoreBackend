package developer.book.backend.entities.concretes

import jakarta.persistence.*

@Entity
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var title: String? = null,
        @ManyToMany
        @JoinTable(
                name = "book_author",
                joinColumns = [JoinColumn(name = "book_id")],
                inverseJoinColumns = [JoinColumn(name = "author_id")]
        )
        var authors: List<Author>? = null,
        var isbn: String? = null,
        var publisher: String? = null,
        var publishedDate: String? = null,
        var pageCount: Int? = null,
        @Column(length = 1000000)
        var description: String? = null,
        @OneToOne(cascade = [CascadeType.ALL], optional = true)
        var imageLinks: ImageLinks? = null
)
