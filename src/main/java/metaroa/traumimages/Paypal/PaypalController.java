package metaroa.traumimages.Paypal;

import metaroa.traumimages.Plan.Plan;
import metaroa.traumimages.Plan.PlanRepository;
import metaroa.traumimages.User.User;
import metaroa.traumimages.User.UserRepository;
import metaroa.traumimages.dto.Order;
import metaroa.traumimages.dto.PaypalResponseDTO;
import metaroa.traumimages.dto.SimpleMessageResponse;
import metaroa.traumimages.errorHandling.InternalServerError;
import metaroa.traumimages.errorHandling.NotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@RestController
public class PaypalController {
    private final PaypalRepository repository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    public PaypalController(PaypalRepository paypalRepository, PlanRepository planRepository, UserRepository userRepository){
        this.repository = paypalRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/placeOrder")
    public SimpleMessageResponse placeOrder(@RequestBody Order payload, HttpServletRequest request){
        Optional<Plan> toCheck = planRepository.findById(payload.getPlan_id());
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findUserByEmail(principal.getName());
        if(toCheck.isPresent()){
            Plan plan = toCheck.get();
            CreatePaypalOrder createPaypalOrder = new CreatePaypalOrder();
            try{
                PaypalResponseDTO paypalResponseDTO = createPaypalOrder.createOrder(plan.getPrice());
                PaypalOrder paypalOrder = new PaypalOrder();
                paypalOrder.setPaypalPaymentId(paypalResponseDTO.getId());
                paypalOrder.setCreationTime(new Date());
                paypalOrder.setPrice(plan.getPrice());
                paypalOrder.setIsCompleted(false);
                paypalOrder.setHref("https://www.paypal.com/checkoutnow?token=" + paypalResponseDTO.getId());
                paypalOrder.setUser(user);
                repository.save(paypalOrder);
                SimpleMessageResponse messageResponse = new SimpleMessageResponse("https://www.paypal.com/checkoutnow?token=" + paypalResponseDTO.getId());
                return messageResponse;
            }catch(Exception ex){
                throw new InternalServerError();
            }
        }else{
            throw new NotFoundException();
        }
    }
}
