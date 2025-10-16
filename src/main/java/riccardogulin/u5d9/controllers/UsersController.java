package riccardogulin.u5d9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import riccardogulin.u5d9.entities.User;
import riccardogulin.u5d9.exceptions.ValidationException;
import riccardogulin.u5d9.payload.NewUserDTO;
import riccardogulin.u5d9.payload.NewUserPayload;
import riccardogulin.u5d9.services.UsersService;

import java.io.IOException;
import java.util.UUID;

/*

1. GET http://localhost:3001/users
2. POST http://localhost:3001/users (+ request body)
3. GET http://localhost:3001/users/{userId}
4. PUT http://localhost:3001/users/{userId} (+ request body)
5. DELETE  http://localhost:3001/users/{userId}

*/

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	private UsersService usersService;

	// 1. GET http://localhost:3001/users
	@GetMapping
	public Page<User> findAll(@RequestParam(defaultValue = "0") int page,
	                          @RequestParam(defaultValue = "10") int size,
	                          @RequestParam(defaultValue = "id") String sortBy) {
		// Mettere dei valori di default nei query params è solitamente una buona idea per far si che non
		// ci siano errori se il client non li passa
		return this.usersService.findAll(page, size, sortBy);
	}

	// 2. POST http://localhost:3001/users (+ request body)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {
		// @Validated serve per "attivare" la validazione
		// BindingResult è un oggetto che contiene tutti gli errori e anche dei metodi comodi da usare tipo .hasErrors()
		if (validationResult.hasErrors()) {

			throw new ValidationException(validationResult.getFieldErrors()
					.stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
		}
		return this.usersService.save(payload);
	}

	// 3. GET http://localhost:3001/users/{userId}
	@GetMapping("/{userId}")
	public User findById(@PathVariable UUID userId) {
		return this.usersService.findById(userId);
	}


	// 4. PUT http://localhost:3001/users/{userId} (+ request body)
	@PutMapping("/{userId}")
	public User findByIdAndUpdate(@PathVariable UUID userId, @RequestBody NewUserPayload payload) {
		return this.usersService.findByIdAndUpdate(userId, payload);
	}

	// 5. DELETE  http://localhost:3001/users/{userId}
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void findByIdAndDelete(@PathVariable UUID userId) {
		this.usersService.findByIdAndDelete(userId);
	}

	@PatchMapping("/{userId}/avatar")
	public String uploadImage(@RequestParam("avatar") MultipartFile file) throws IOException {
		// "avatar" deve corrispondere ESATTAMENTE al nome del campo del MultiPart che contiene il file
		// che è quello che verrà inserito dal frontend
		// Se non corrisponde non troverò il file
		System.out.println(file.getSize());
		System.out.println(file.getOriginalFilename());
		return this.usersService.uploadAvatar(file);
	}
}
