package metaroa.traumimages.Plan;

import metaroa.traumimages.dto.PlanDTO;
import metaroa.traumimages.dto.SimpleMessageResponse;
import metaroa.traumimages.errorHandling.IncompletePayloadException;
import metaroa.traumimages.errorHandling.InternalServerError;
import metaroa.traumimages.errorHandling.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PlanController {
    private final PlanRepository repository;
    private boolean isPayloadComplete(PlanDTO payload){
        return payload.getDuration() != null &&
                payload.getTitle() != null &&
                payload.getPrice() != null &&
                payload.getCapacity() != null &&
                payload.getDescription() != null;
    }

    public PlanController(PlanRepository repository){
        this.repository = repository;
    }

    @GetMapping("/listPlans")
    public List<Plan> getAll(){
        try{
            List<Plan> response =  repository.findAll();
            return response;
        }catch (Exception ex){
            throw new InternalServerError();
        }
    }

    @PutMapping("/modifyPlan/{id}")
    public SimpleMessageResponse modifyPlan(@PathVariable Long id, @RequestBody PlanDTO payload){
        if(isPayloadComplete(payload)){
            Optional<Plan> check = repository.findById(id);
            if(check.isPresent()){
                Plan toModify = check.get();
                toModify.setCapacity(payload.getCapacity());
                toModify.setDescription(payload.getDescription());
                toModify.setPrice(payload.getPrice());
                toModify.setTitle(payload.getTitle());
                toModify.setDuration(payload.getDuration());
                try{
                    repository.save(toModify);
                }catch(Exception ex){
                    throw new InternalServerError();
                }finally {
                    SimpleMessageResponse response = new SimpleMessageResponse("Plan modified successfully");
                    return response;
                }
            }else{
                throw new NotFoundException();
            }
        }else{
            throw new IncompletePayloadException();
        }
    }

    @PostMapping("/newPlan")
    public SimpleMessageResponse newPlan(@RequestBody PlanDTO payload){
        if(isPayloadComplete(payload)){
            Plan toSave = new Plan();
            toSave.setDuration(payload.getDuration());
            toSave.setTitle(payload.getTitle());
            toSave.setPrice(payload.getPrice());
            toSave.setCapacity(payload.getCapacity());
            toSave.setDescription(payload.getDescription());
            try{
                repository.save(toSave);
                SimpleMessageResponse response = new SimpleMessageResponse("new plan added successfully");
                return response;
            }catch (Exception ex){
                throw new InternalServerError();
            }
        }else{
            throw new IncompletePayloadException();
        }
    }

    @DeleteMapping("/deletePlan/{id}")
    public SimpleMessageResponse delete(@PathVariable Long id){
        Optional<Plan> toDelete = repository.findById(id);
        if(toDelete.isPresent()){
            try{
                repository.delete(toDelete.get());
                SimpleMessageResponse response = new SimpleMessageResponse("plan removed successfully");
                return response;
            }catch(Exception ex){
                throw new InternalServerError();
            }
        }else{
            throw new NotFoundException();
        }
    }
}
