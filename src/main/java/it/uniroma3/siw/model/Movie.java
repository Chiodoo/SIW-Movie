package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Movie {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @NotBlank
        private String title;

        @NotNull
        @Min(1891)
        @Max(2025)
        private Integer year;
        private String urlImage;

        @ManyToMany
        private List<Artist> actors;

        @ManyToOne
        private Artist director;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public String getUrlImage() {
            return urlImage;
        }
        
        public void setUrlImage(String urlImage) {
            this.urlImage = urlImage;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((title == null) ? 0 : title.hashCode());
            result = prime * result + ((year == null) ? 0 : year.hashCode());
            return result;
        }
    
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Movie other = (Movie) obj;
            if (title == null) {
                if (other.title != null)
                    return false;
            } else if (!title.equals(other.title))
                return false;
            if (year == null) {
                if (other.year != null)
                    return false;
            } else if (!year.equals(other.year))
                return false;
            return true;
        }

        public List<Artist> getActors() {
            return actors;
        }

        public void setActors(List<Artist> actors) {
            this.actors = actors;
        }

        public Artist getDirector() {
            return director;
        }

        public void setDirector(Artist director) {
            this.director = director;
        }
        
}
