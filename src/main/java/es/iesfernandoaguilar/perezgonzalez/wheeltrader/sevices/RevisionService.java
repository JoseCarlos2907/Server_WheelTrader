package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Revision;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevisionService {
    @Autowired
    private RevisionRepository revisionRepository;

    public void save(Revision revision) {
        this.revisionRepository.save(revision);
    }
}
