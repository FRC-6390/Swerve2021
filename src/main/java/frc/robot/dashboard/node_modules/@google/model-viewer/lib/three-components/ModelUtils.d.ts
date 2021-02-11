import { Object3D, Vector3 } from 'three';
import { GLTF } from 'three/examples/jsm/loaders/GLTFLoader';
export interface FullGLTF extends GLTF {
    parser?: any;
}
/**
 * Fully clones a parsed GLTF, including correct cloning of any SkinnedMesh
 * objects.
 *
 * NOTE(cdata): This is necessary due to limitations of the Three.js clone
 * routine on scenes. Without it, models with skeletal animations will not be
 * cloned properly.
 *
 * @see https://github.com/mrdoob/three.js/issues/5878
 */
export declare const cloneGltf: (gltf: FullGLTF) => FullGLTF;
/**
 * Moves Three.js objects from one parent to another
 */
export declare const moveChildren: (from: Object3D, to: Object3D) => void;
/**
 * Performs a reduction across all the vertices of the input model and all its
 * children. The supplied function takes the reduced value and a vertex and
 * returns the newly reduced value. The value is initialized as zero.
 *
 * Adapted from Three.js, @see https://github.com/mrdoob/three.js/blob/7e0a78beb9317e580d7fa4da9b5b12be051c6feb/src/math/Box3.js#L241
 */
export declare const reduceVertices: (model: Object3D, func: (value: number, vertex: Vector3) => number) => number;
