package controllers.composers;

import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class LoggedUser extends play.mvc.Action.Simple{

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        if(ctx.session().get("user") != null){
            return delegate.call(ctx);
        }else{
            CompletableFuture<Result> result = new CompletableFuture<>();
            result.complete(redirect("/"));
            return result;
        }
    }
}
