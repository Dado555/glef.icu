<template>
  <div class="container mx-auto px-4 py-16">
    <app-tabs class="w-12/12 lg:w-12/12 mx-auto mb-16" :variant="'vertical'" > <!--:tabList="tabList" :users="users" >-->
      <template v-slot:tabPanel-1/>
<!--      <template v-slot:tabPanel-2/>-->
      <template v-slot:tabPanel-3/>
    </app-tabs>
  </div>
</template>

<script>
import AppTabs from "@/components/users/AppTabs";

export default {
  name: "Users",

  components: {
    AppTabs
  },
}
</script>

<style scoped>

</style>