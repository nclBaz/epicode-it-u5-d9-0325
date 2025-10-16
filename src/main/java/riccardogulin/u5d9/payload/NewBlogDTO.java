package riccardogulin.u5d9.payload;

import java.util.UUID;

public record NewBlogDTO(String title, String content, String category, UUID authorId) {
}
