package MyStore.services;

import MyStore.entities.SubCategory;
import MyStore.exceptions.NotFoundException;
import MyStore.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private MainCategoryService mainCategoryService;

    public void saveSubCategory (SubCategory subCategory) {
        subCategoryRepository.save(subCategory);
    }

    public SubCategory getSubCategoryById (long subCategoryId) throws NotFoundException {
        return subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new NotFoundException("SubCategory",subCategoryId));
    }

    public SubCategory getSubCategoryByName (String name) throws NotFoundException {
        return subCategoryRepository.findByName(name).orElseThrow(() -> new NotFoundException("SubCategory", name));
    }

    public List<SubCategory> getSubCategories() {
        return subCategoryRepository.findAll();
    }
}
