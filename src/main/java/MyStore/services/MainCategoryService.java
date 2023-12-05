package MyStore.services;

import MyStore.entities.MainCategory;
import MyStore.exceptions.NotFoundException;
import MyStore.repositories.MainCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainCategoryService {
    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    public void saveMainCategory(MainCategory mainCategory) {
        mainCategoryRepository.save(mainCategory);
    }

    public MainCategory getMainCategoryById(long mainCategoryId) throws NotFoundException {
        return mainCategoryRepository.findById(mainCategoryId).orElseThrow(() -> new NotFoundException("MainCategory", mainCategoryId));
    }

    public MainCategory getMainCategoryByName(String name) throws NotFoundException {
        return mainCategoryRepository.findByMainCategoryName(name).orElseThrow(() -> new NotFoundException("MainCategory", name));
    }

    public List<MainCategory> getMainCategories() {
        return mainCategoryRepository.findAll();
    }
}
