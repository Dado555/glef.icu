import VueRouter from "vue-router";
import HomeComponent from "@/components/HomeComponent";

let router = new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/",
            name: "home",
            component: HomeComponent
        }
    ]
})

export default router;