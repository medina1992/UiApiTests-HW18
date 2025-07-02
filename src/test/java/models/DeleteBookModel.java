package models;

import lombok.Data;

@Data
public class DeleteBookModel {
    String userId, isbn;
}