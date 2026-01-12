package com.example.student_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddStudentRequestDto {

    @Schema(example = "prasad")
    @NotBlank(message = "Name {not.blank}")
//    @Pattern(regexp = "^[ a-zA-Z]{3,20}$", message = "Name {max.chars.20}")

    // generalized way
//    @Size(min = 3, max = 20, message = "Name should be min 3 chars and max 20 chars")
    // specified way
    @Size.List({
            @Size(min = 3, message = "Name {not.lt} {min} {chars}"),
            @Size(max = 20, message = "Name {not.gt} {max} {chars}")
    })
    private String name;

    @Schema(example = "101")
    @NotBlank(message = "Roll Number {not.blank}")
    @Pattern(regexp = "^[ a-zA-Z0-9]{0,20}$", message = "Roll Number {max.chars.20}")
    private String rollNumber;

    @Schema(example = "95.82")
//    @NotBlank(message = "Average {not.blank}") // won't work with Double
    @NotNull(message = "Average {not.blank}")
    @PositiveOrZero(message = "Average {should.positive}")

    private Double average;

    @Schema(example = "btech")
    @NotBlank(message = "Course {not.blank}")
    @Pattern(regexp = "^[ a-zA-Z]{0,20}$", message = "Course {max.chars.20}")
    private String course;

    @Schema(example = "cse")
    @NotBlank(message = "Branch {not.blank}")
    @Pattern(regexp = "^[ a-zA-Z]{0,20}$", message = "Branch {max.chars.20}")
    private String branch;

    @Schema(example = "aven")
    @NotBlank(message = "College {not.blank}")
    @Pattern(regexp = "^[ a-zA-Z]{0,20}$", message = "College {max.chars.20}")
    private String college;

    @Schema(example = "A")
    @NotNull(message = "Grade {not.blank}")
//    @NotBlank(message = "Grade {not.blank}") // @NotBlank won't work with char/Character
//    @Pattern(regexp = "^[A]$", message = "Grade {should.char_1} from [A-F]") // need to figure out
    private Character grade;

}
