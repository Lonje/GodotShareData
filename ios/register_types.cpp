#include <version_generated.gen.h>

#include <core/class_db.h>
#include <core/engine.h>
#include "register_types.h"

#include "src/godotShareData.h"

void register_share_types() {
    Engine::get_singleton()->add_singleton(Engine::Singleton("GodotShareData", memnew(GodotShareData)));
}

void unregister_share_types() {
}
