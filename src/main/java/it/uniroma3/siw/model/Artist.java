package it.uniroma3.siw.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Artist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @NotBlank
        private String name;
        @NotBlank
        private String surname;
        @NotNull
        private LocalDate birth;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSurname() {
                return surname;
        }

        public void setSurname(String surname) {
                this.surname = surname;
        }

        public LocalDate getBirth() {
                return birth;
        }
        
        public void setBirth(LocalDate nascita) {
                this.birth = nascita;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((name == null) ? 0 : name.hashCode());
                result = prime * result + ((surname == null) ? 0 : surname.hashCode());
                result = prime * result + ((birth == null) ? 0 : birth.hashCode());
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
                Artist other = (Artist) obj;
                if (name == null) {
                        if (other.name != null)
                                return false;
                } else if (!name.equals(other.name))
                        return false;
                if (surname == null) {
                        if (other.surname != null)
                                return false;
                } else if (!surname.equals(other.surname))
                        return false;
                if (birth == null) {
                        if (other.birth != null)
                                return false;
                } else if (!birth.equals(other.birth))
                        return false;
                return true;
        }
}
