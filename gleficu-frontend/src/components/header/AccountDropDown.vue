<template>
  <div class="relative" style="margin-right: 50px">
    <button @click="isOpen = !isOpen" class="focus:outline-none">
      <img
        src="@/assets/images/avatar.jpg"
        alt=""
        class="h-10 rounded-full mr-1"
      />
    </button>

    <button
      @click="isOpen = false"
      v-if="isOpen"
      class="fixed top-0 right-0 bottom-0 w-full h-full cursor-default bg-black opacity-50 "
    ></button>

    <div
      v-if="isOpen"
      class="absolute bg-white py-2 rounded-lg w-48 right-0 mr-3 shadow-xl"
    >
      <a
        class="text-gray-500 block px-4 py-2 hover:bg-indigo-500 hover:text-white"
        >Account Setting
      </a>
      <a
        class="text-gray-500 block px-4 py-2 hover:bg-indigo-500 hover:text-white"
        >Support
      </a>
      <a
        @click.prevent="logout"
        href=""
        class="text-gray-500 block px-4 py-2 hover:bg-indigo-500 hover:text-white"
        >Logout
      </a>
    </div>
  </div>
</template>

<script>
export default {
  created() {
    const handleEscape = (e) => {
      if (e.key == "Esc" || e.key == "Escape") {
        this.isOpen = false;
      }
    };

    document.addEventListener("keydown", handleEscape);

    this.$once("hook:beforeDestroy", () => {
      document.removeEventListener("keydown", handleEscape);
    });
  },
  data() {
    return {
      isOpen: false,
    };
  },

  methods: {
    logout() {
      this.$store.dispatch("setUserLoggedIn", false);
    },
  },
};
</script>

<style></style>
