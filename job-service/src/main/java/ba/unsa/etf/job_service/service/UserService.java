package ba.unsa.etf.job_service.service;

import ba.unsa.etf.job_service.dto.UserDTO;
import ba.unsa.etf.job_service.model.User;
import ba.unsa.etf.job_service.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO getUserDTOByUUID(String userUUID) {
        User user = userRepository.findByUserUUID(userUUID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }
}
