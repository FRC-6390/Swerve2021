import { MeshStandardMaterial } from 'three';
declare const $mipmapMaterial: unique symbol;
declare const $scene: unique symbol;
declare const $flatCamera: unique symbol;
declare const $tempTarget: unique symbol;
/**
 * The RoughnessMipmapper class allows for the custom generation of mipmaps for
 * the roughness map of a MeshStandardMaterial. This custom mipmapping is based
 * on the normal map, so that as normal variation is lost at deeper mip levels,
 * that loss is encoded as increased roughness to keep reflections consistent
 * across zoom levels and reduce aliasing.
 */
export declare class RoughnessMipmapper {
    private [$mipmapMaterial];
    private [$scene];
    private [$flatCamera];
    private [$tempTarget];
    constructor();
    generateMipmaps(material: MeshStandardMaterial): void;
}
export {};
