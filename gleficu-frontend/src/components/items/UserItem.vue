<template>
  <div>
    <router-link :to="`/user/${this.user.id}`">
      <img src="@/assets/images/avatar.jpg" alt=""/>

      <h3>{{ user.name }}</h3>
<!--      <p class="text-gray-400">-->
<!--        {{ knownFor }}-->
<!--      </p>-->
    </router-link>
    <button class="px-4 py-1.5 rounded-lg text-blue-600 bg-white shadow-xl">Ban user</button>
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