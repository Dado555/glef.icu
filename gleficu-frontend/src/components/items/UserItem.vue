<template>
  <div>
    <router-link :to="`/user/${this.user.id}`">
      <img src="@/assets/images/avatar.jpg" alt=""/>

      <h3>{{ user.username }}</h3>
<!--      <p class="text-gray-400">-->
<!--        {{ knownFor }}-->
<!--      </p>-->
      <button class="px-4 py-1.5 rounded-lg bg-red-500  bg-white shadow-xl" v-if="user.canBeBanned">Can be banned</button>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "UserItem",
  props: {
    user: {
      required: true,
    },
    // canUserBeBanned: true
  },
}
</script>

<style scoped>

</style>