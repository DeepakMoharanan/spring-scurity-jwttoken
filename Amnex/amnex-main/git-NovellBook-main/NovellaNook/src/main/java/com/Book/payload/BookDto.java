package com.Book.payload;
public class BookDto {
        private Long id;
        private String name;
        private String author;
        private int publishedYear;

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

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public int getPublishedYear() {
                return publishedYear;
        }

        public void setPublishedYear(int publishedYear) {
                this.publishedYear = publishedYear;
        }
}
