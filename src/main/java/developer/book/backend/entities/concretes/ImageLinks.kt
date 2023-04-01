package developer.book.backend.entities.concretes

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
data class ImageLinks(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var smallThumbnail: String? = null,
        var thumbnail: String? = null,



)
