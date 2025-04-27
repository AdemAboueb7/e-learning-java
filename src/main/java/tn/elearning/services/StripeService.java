package tn.elearning.services;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.model.Product;
import com.stripe.model.Price;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.PriceCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import tn.elearning.entities.Abonnement;

public class StripeService {
    private static final Dotenv dotenv = Dotenv.load();
    public StripeService() {
        String stripeApiKey = dotenv.get("STRIPE_API_KEY");
        Stripe.apiKey = stripeApiKey;
    }


    public Session createCheckoutSession(Abonnement abonnement) throws Exception {
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName(abonnement.getType())
                .build();

        Product product = Product.create(productParams);
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency("eur")
                .setUnitAmount((long) (abonnement.getPrix() * 100)) // en centimes
                .setProduct(product.getId())
                .build();

        Price price = Price.create(priceParams);

        SessionCreateParams sessionParams = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4242/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:4242/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(price.getId())
                                .build()
                )
                .build();
        return Session.create(sessionParams);
    }

    public Session retrieveSession(String sessionId) throws Exception {
        return Session.retrieve(sessionId);
    }
}
